require '../src/conflict_handler'

class Trait
  include ConflictHandler

  attr_accessor :methods, :traits, :conflict_resolution

  def initialize
    @methods = Hash.new
    @conflict_resolution = ConflictResolution.new(ConflictType::DEFAULT, nil)
  end

  def method(name, &block)
    @methods[name] = block
  end

  def self.define &definition_trait
    @trait = Trait.new
    @trait.instance_eval(&definition_trait)
    @trait
  end

  def name name
    Object.const_set(name, self)
  end

  def +(otherTrait)
    self.methods_merge(otherTrait)
    self
  end

  def methods_merge(otherTrait)
    self.methods.merge!(otherTrait.methods)do
    |key|
        conflict(key, otherTrait, self.methods[key])
     end
  end

  def - (method)
    if(self.methods.has_key?(method))
      self.methods.delete(method)
    else
      raise StandardError, "No existe el metodo #{method} a remover"
    end
    self
  end

  def << (tuple_method)
    self.methods[tuple_method.flatten.at(1)] = self.methods.delete tuple_method.flatten.at(0)
    self
  end

  def <= (conflict_resolution)
    self.conflict_resolution = conflict_resolution
    self
  end

end
