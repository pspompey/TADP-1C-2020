require '../src/conflict_handler'

class Trait
  include ConflictHandler

  attr_accessor :methods, :conflict_resolution

  def initialize
    @methods = Hash.new
    @conflict_resolution = ConflictResolution.new(ConflictType::DEFAULT)
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

  def clone
    object_clon = Trait.new
    object_clon.methods = self.methods.clone
    object_clon.conflict_resolution = self.conflict_resolution.clone
    object_clon
  end

  def +(otherTrait)
    trait = self.clone
    trait.methods_merge(otherTrait)
    trait
  end

  def methods_merge(otherTrait)
    self.methods.merge!(otherTrait.methods)do
    |key|
        conflict(key, otherTrait, self.methods[key])
     end
  end

  def - (method)
    trait = self.clone
    if(trait.methods.has_key?(method))
      trait.methods.delete(method)
    else
      raise StandardError, "No existe el metodo #{method} a remover"
    end
    trait
  end

  def << (tuple_method)
    trait = self.clone
    trait.methods[tuple_method.flatten.at(1)] = trait.methods.delete tuple_method.flatten.at(0)
    trait
  end

  def <= (conflict_resolution)
    trait = self.clone
    trait.conflict_resolution = conflict_resolution
    trait
  end

end
