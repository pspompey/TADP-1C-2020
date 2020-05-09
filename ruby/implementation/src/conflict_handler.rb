require '../src/conflict_resolution'

module ConflictHandler

  def conflict(name_method, conflict_resolution, current_method, other_trait_method)
    case conflict_resolution.conflict_type
    when ConflictType::DEFAULT
        raise DuplicateMethodError, "Conflicto con el metodo #{name_method}"
    when ConflictType::EXEC_ALL
      exec_all_resolution(current_method, other_trait_method)
    when ConflictType::FOLD
      fold_resolution(current_method, other_trait_method, conflict_resolution.functions)
    when ConflictType::EXEC_IF
      exec_if_resolution(current_method, other_trait_method, conflict_resolution.functions)
    when ConflictType::CUSTOM
      custom_resolution(current_method, other_trait_method, conflict_resolution.functions)
    else
      raise StandardError, "Tipo de resolución inexistente"
    end
  end

  private

  def custom_resolution(current_method, other_trait_method, other_trait_functions)
    proc do |*args|
      other_trait_functions.fetch(0).call(current_method, other_trait_method, *args)
    end
  end

  def exec_if_resolution(current_method, other_trait_method, other_trait_functions)
    lambda do |*args|
      if (other_trait_functions.fetch(0).call(current_method.call(*args)))
        current_method.call(*args)
      elsif (other_trait_functions.fetch(0).call(other_trait_method.call(*args)))
        other_trait_method.call(*args)
      else
        other_trait_functions.fetch(1).call(*args)
      end
    end
  end

  def fold_resolution(current_method, other_trait_method, other_trait_functions)
    lambda do |*args|
      other_trait_functions.fetch(0).call(current_method.call(*args), other_trait_method.call(*args))
    end
  end

  def exec_all_resolution(current_method, other_trait_method)
    Proc.new do |*args|
      @results = []
      @results << current_method.call(*args)
      @results << other_trait_method.call(*args)
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

