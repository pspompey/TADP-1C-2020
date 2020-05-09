class ConflictResolutionExecAll

  def solve(current_method, other_trait_method, _)
    Proc.new do |*args|
      @results = []
      @results << current_method.call(*args)
      @results << other_trait_method.call(*args)
      @results.each { |_, result| result }
    end
  end

end

class ConflictResolutionFold

  attr_accessor :function

  def solve(current_method, other_trait_method, _)
    func = function
    lambda do |*args|
      func.call(current_method.call(*args), other_trait_method.call(*args))
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
      if (cond.call(current_method.call(*args)))
        current_method.call(*args)
      elsif (cond.call(other_trait_method.call(*args)))
        other_trait_method.call(*args)
      else
        opt.call(*args)
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
      func.call(current_method, other_trait_method, *args)
    end
  end

  def initialize(function:)
    @function = function
  end

end

class ConflictResolutionDefault

  def solve(_, _, method_name)
    Proc.new do
      raise DuplicateMethodError, "Conflicto con el metodo #{method_name}"
    end
  end

end

class DuplicateMethodError < StandardError
end