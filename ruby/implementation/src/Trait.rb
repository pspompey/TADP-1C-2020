require_relative 'Estrategia.rb'

class Trait
  attr_reader :trait_methods

  include Estrategia

  def initialize(&bloque)
    @trait_methods = {}
    instance_eval(&bloque)
  end

  def name(nombre_Trait)
    Object.const_set(nombre_Trait, self)
  end

  def method(nombre, &cuerpo_metodo)
    @trait_methods.update({nombre => cuerpo_metodo})
  end

  def self.define(&bloque)
    Trait.new(&bloque)
  end

  def generar_trait_con_metodos(metodos)
    return Trait.new do
      @trait_methods.update(metodos)
    end
  end

  def <<(simbolos_metodos)
    metodos_trait_con_alias = @trait_methods.clone
    metodos_trait_con_alias[simbolos_metodos[1]] = metodos_trait_con_alias.delete(simbolos_metodos[0])

    generar_trait_con_metodos(metodos_trait_con_alias)
  end

  def +(otro_trait, estrategia = "resolucion_basica")

    # ESTRATEGIA ES UN MIXIN

    metodos_para_agregar = send estrategia,self,otro_trait

    # CADA ESTRATEGIA ES UN MODULE
    # metodos_para_agregar = estrategia.resolver_conflicto(self, otro_trait)

    # SIN ESTRATEGIAS
    # metodos_para_agregar = @trait_methods.merge(otro_trait.trait_methods) { |nombre_metodo| estrategia }


    generar_trait_con_metodos(metodos_para_agregar)
  end

  def -(metodo)
    metodos_de_trait = @trait_methods.filter { |nombre_metodo| nombre_metodo != metodo }
    generar_trait_con_metodos(metodos_de_trait)
  end


end


class MetodosDeIgualNombreException < StandardError
end



# TODO: Entonces si se suman el trait T1 y el trait T2 y ambos tienen un mensaje m entonces en la clase se deberá generar un mensaje m que
# TODO: llame a t1 m y luego a t2 m, siendo estos alias a los respectivos traits.

