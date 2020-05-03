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
      custom_resolution(current_method, key, other_trait)
    else
      raise StandardError, "Tipo de resolución inexistente"
    end
  end

  private

  def custom_resolution(current_method, key, other_trait)
    proc do |*args|
    other_trait.conflict_resolution.functions.fetch(0).call(current_method.call(*args),
                                                            other_trait.methods[key].call(*args), *args)
    end
  end

  def exec_if_resolution(current_method, key, other_trait)
    function = other_trait.conflict_resolution.functions.fetch(0)
    lambda do |*args|
      if (function.call(current_method.call(*args)))
        current_method.call(*args)
      elsif (function.call(other_trait.methods[key].call(*args)))
        other_trait.methods[key].call(*args)
      else
        function_optional = other_trait.conflict_resolution.functions.fetch(1)
        function_optional.call(*args)
      end
    end
  end

  def fold_resolution(current_method, key, other_trait)
    function = other_trait.conflict_resolution.functions.fetch(0)
    lambda do |*args|
      function.call(current_method.call(*args), other_trait.methods[key].call(*args))
    end
  end

  def exec_all_resolution(current_method, key, other_trait)
    Proc.new do |*args|
      @results = []
      @results << current_method.call(*args)
      @results << other_trait.methods[key].call(*args)
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

