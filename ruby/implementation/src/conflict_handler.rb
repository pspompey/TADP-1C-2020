module ConflictHandler

  def conflict(key, other_trait, current_method)
    case other_trait.conflict_resolution.conflict_type
    when ConflictType::DEFAULT
        raise DuplicateMethodError, "Conflicto con el metodo #{key}"
    when ConflictType::EXEC_ALL
      exec_all_resolution(current_method, key, other_trait)
    when ConflictType::FOLD
      fold_resolution(current_method, key, other_trait)
    when ConflictType::EXEC_IF
      exec_if_resolution(current_method, key, other_trait)
    when ConflictType::CUSTOM
      custom_resolution(other_trait)
    else
      raise StandardError, "Tipo de resolución inexistente"
    end
  end

  private

  def custom_resolution(other_trait)
    Proc.new do |*args|
      @results = []
      other_trait.conflict_resolution.functions.each do |function|
        @results << function.call(*args)
      end
      @results.each { |_, result| result }
    end
  end

  def exec_if_resolution(current_method, key, other_trait)
    function = other_trait.conflict_resolution.functions.fetch(0) {raise StandardError, "Número de parámetros incorrectos"}
    lambda do |*args|
      res1 = current_method.call(*args)
      res2 = other_trait.methods[key].call(*args)

      if (function.call(res1))
        res1
      elsif (function.call(res2))
        res2
      else
        function_optional = other_trait.conflict_resolution.functions.fetch(1) {raise StandardError, "No definió una función opcional"}
        function_optional.call(*args)
      end
    end
  end

  def fold_resolution(current_method, key, other_trait)
    function = other_trait.conflict_resolution.functions.fetch(0) {raise StandardError, "Número de parámetros incorrectos"}
    lambda do |*args|
      res1 = current_method.call(*args)
      res2 = other_trait.methods[key].call(*args)

      function.call(res1, res2)
    end
  end

  def exec_all_resolution(current_method, key, other_trait)
    Proc.new do |*args|
      @results = []
      @results[0] = current_method.call(*args)
      @results[1] = other_trait.methods[key].call(*args)
      @results.each { |_, result| result }
    end
  end

end

class DuplicateMethodError < StandardError
end

module ConflictType
  DEFAULT = 0
  EXEC_ALL = 1
  FOLD = 2
  EXEC_IF = 3
  CUSTOM = 4
end

class ConflictResolution

  attr_accessor  :conflict_type, :functions

  def initialize(conflictType, functions_resolution = {})
    @conflict_type = conflictType
    @functions = functions_resolution
  end

end