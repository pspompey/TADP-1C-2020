# se decidió crear una clase para cada resolución de conflicto
# que funcione con el método solve polimórficamente

class ConflictResolutionExecAll

  def solve(current_method, other_trait_method, _)
    Proc.new do |*args|
      @results = []
      @results << instance_exec(*args, &current_method)
      @results << instance_exec(*args, &other_trait_method)
      @results
    end
  end

end

class ConflictResolutionFold

  attr_accessor :function

  def solve(current_method, other_trait_method, _)
    func = function
    lambda do |*args|
      instance_exec(current_method.call(*args), other_trait_method.call(*args), &func)
    end
  end

  def initialize(function:)
    @function = function
  end

end

class ConflictResolutionExecIf

  attr_accessor :condition, :option

  def solve(current_method, other_trait_method, _)
    cond = condition
    opt = option
    lambda do |*args|
      if (instance_exec(instance_exec(*args, &current_method), &cond))
        instance_exec(*args, &current_method)
      elsif (instance_exec(instance_exec(*args, &other_trait_method), &cond))
        instance_exec(*args, &other_trait_method)
      else
        instance_exec(*args, &opt)
      end
    end
  end

  def initialize(condition:, option:)
    @condition = condition
    @option = option
  end

end

class ConflictResolutionCustom

  attr_accessor :function

  def solve(current_method, other_trait_method, _)
    func = function
    proc do |*args|
      instance_exec(current_method, other_trait_method, *args, &func)
    end
  end

  def initialize(function:)
    @function = function
  end

end

class ConflictResolutionDefault

  # se encierra dentro de un proc para que se ejecute cuando se invoque el método conflictivo
  def solve(_, _, method_name)
    Proc.new do
      raise DuplicateMethodError, "Conflicto con el metodo #{method_name}"
    end
  end

end

class DuplicateMethodError < StandardError
end