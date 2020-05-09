require_relative 'Trait.rb'
require_relative 'Symbol'

Class.define_method(:uses) do |un_trait|
  un_trait.trait_methods.keys { |nombre_metodo_en_trait| instance_methods.any? { |metodo| metodo == nombre_metodo_en_trait } }
      .each { |nombre_metodo| define_method(nombre_metodo, un_trait.trait_methods.fetch(nombre_metodo)) }
end