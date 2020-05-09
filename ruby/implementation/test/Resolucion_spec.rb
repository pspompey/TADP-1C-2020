require 'rspec'
require_relative '../src/Class'
require_relative '../src/Trait'

describe 'Resolucion de conflictos' do
  it ('En caso de conflicto que ejecute todos los mensajes conflictivos en orden de aparición.') do
    Trait.define do
      name :Ingles
      method :saludo do
        puts "Hello"
      end
      method :ignorar do
        puts "..."
      end
    end

    Trait.define do
      name :Hawaiano
      method :saludo do
        puts "Aloha"
      end
    end


    class Persona
      uses Ingles.+(Hawaiano, 'ejecutar_todo')
    end

    juan = Persona.new
    expect(juan.saludo).to eq(nil)
    juan.ignorar
    juan.saludo
  end
end