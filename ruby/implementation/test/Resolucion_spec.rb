require 'rspec'
require_relative '../src/Class'
require_relative '../src/Trait'

Trait.define do
  name :Ingles
  method :saludo do
    "Hello"
  end
  method :ignorar do
    puts "..."
  end
end

Trait.define do
  name :Hawaiano
  method :saludo do
    "Aloha"
  end
end


describe 'Resolucion de conflictos' do

  it ('En caso de conflicto que ejecute todos los mensajes conflictivos en orden de aparición.') do

    class Persona
      uses Ingles.+(Hawaiano, 'ejecutar_todo')
    end

    juan = Persona.new
    expect(juan.saludo).to eq(["Hello", "Aloha"])

  end


  it ('En caso de conflicto que haga fold con los resultados de cada metodo') do
    class Persona
      uses Ingles.+(Hawaiano, 'aplicar_foldeo', "<<")
    end

    juan = Persona.new
    expect(juan.saludo).to eq("HelloAloha")

  end
end