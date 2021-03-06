require_relative '../src/conflict_resolution'

class Trait

  attr_accessor :methods, :conflict_resolution,:methods_resolution

  def initialize
    @methods = Hash.new
    @conflict_resolution = ConflictResolutionDefault.new
    @methods_resolution = Hash.new
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

  #crea un nuevo Trait clonando sus propiedades
  def clone
    object_clon = Trait.new
    object_clon.methods = self.methods.clone
    object_clon.conflict_resolution = self.conflict_resolution.clone
    object_clon
  end

  #suma de Traits
  def +(otherTrait)
    trait = self.clone
    trait.methods_merge(otherTrait)
    trait
  end

  #unión de los método al sumar Traits
  def methods_merge(otherTrait)
    self.methods.merge!(otherTrait.methods)do
    |key|
      if otherTrait.methods_resolution.has_key? key
        otherTrait.methods_resolution[key].solve(self.methods[key], otherTrait.methods[key], key)
      else
        otherTrait.conflict_resolution.solve(self.methods[key], otherTrait.methods[key], key)
      end
     end
  end

  #restarle un método al trait
  def - (method)
    trait = self.clone
    if(trait.methods.has_key?(method))
      trait.methods.delete(method)
    else
      raise StandardError, "No existe el metodo #{method} a remover"
    end
    trait
  end

  #renombrar un método conflictivo
  def << (tuple_method)
    trait = self.clone
    trait.methods[tuple_method.flatten.at(1)] = trait.methods.delete tuple_method.flatten.at(0)
    trait
  end

  #resolución de conflictos mediante una estrategia
  def <= (conflict_resolution)
    trait = self.clone
    if conflict_resolution.instance_of? Hash
      trait.methods_resolution= conflict_resolution
    else
      trait.conflict_resolution = conflict_resolution
    end

    trait
  end

  def define_methods(container_class)
    self.methods.each do |selector, method|
      container_class.define_method(selector, method)
    end
  end
end
