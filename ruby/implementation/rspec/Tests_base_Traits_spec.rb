require 'rspec'
require '../spec/Trait'
require '../spec/Class'
require '../spec/Symbol'
require '../spec/conflict_resolution'


describe 'Tests de Traits' do
  it('Creacion de una instancia de Trait, que posee en un diccionario los metodos definidos') do

    Trait.define do
      name :Saludos
      method :saludo_normal do
        "Hola"
      end
    end

    expect(Saludos.methods.keys).to eq([:saludo_normal])

  end

  it('Clase usa Trait y se le agregan sus metodos, los cuales sus instancias pueden usar') do

    Trait.define do
      name :Saludos2
      method :saludo_normal do
        "Hola"
      end
    end

    class Persona
      uses Saludos2
    end

    juan = Persona.new

    expect(juan.saludo_normal).to eq("Hola")

  end

  it('Clase usa Trait y se le agregan sus metodos, los cuales sus instancias pueden usar con parametros') do

    Trait.define do
      name :Operacion
      method :suma do |numero1, numero2|
        numero1 + numero2
      end
    end

    class Persona
      uses Operacion
    end

    juan = Persona.new

    expect(juan.suma(3, 4)).to eq(7)

  end

  it ('Clase usa Trait pero tiene metodos que coinciden en nombre, usa el que definio originalmente') do
    Trait.define do
      name :Saludos3
      method :saludo_normal do
        "Hola"
      end

      method :saludo_formal do
        "Como le va?"
      end
    end


    class Persona
      uses Saludos3

      def saludo_normal
        "Buenos dias"
      end
    end

    juan = Persona.new
    expect(juan.saludo_formal).to eq("Como le va?")
    expect(juan.saludo_normal).to eq("Buenos dias")

  end


  it ('Suma de Traits') do
    Trait.define do
      name :Multiplicacion
      method :multiplicar do |factor1, factor2|
        factor1 * factor2
      end
    end

    Trait.define do
      name :Division
      method :dividir do |dividendo, divisor|
        dividendo / divisor
      end
    end

    operaciones = Multiplicacion + Division
    expect(operaciones.methods.keys).to eq([:multiplicar, :dividir])
  end

  it ('Suma de Traits con una clase que la usa') do
    Trait.define do
      name :Saludo
      method :saludo_normal do
        "Hola"
      end
    end

    Trait.define do
      name :Despedida
      method :despedida_normal do
        "Chau"
      end
    end


    class Persona
      uses Saludo + Despedida
    end

    juan = Persona.new
    expect(juan.saludo_normal).to eq("Hola")
    expect(juan.despedida_normal).to eq("Chau")
  end


  it ('Suma de Traits con una clase que la usa y metodos que coinciden. Tira excepcion') do
    Trait.define do
      name :Ingles
      method :saludo do
        "Hello"
      end
    end

    Trait.define do
      name :Hawaiano
      method :saludo do
        "Aloha"
      end
    end


    class Persona
      uses Ingles + Hawaiano
    end

    juan = Persona.new
    expect { juan.saludo }.to raise_error(DuplicateMethodError)
  end

  it ('Resta selectores con una clase') do
    Trait.define do
      name :Italiano
      method :saludo do
        "Ciao"
      end
      method :despedida do
        "Ciao"
      end
    end

    Trait.define do
      name :Frances
      method :saludo do
        "Alo"
      end
    end


    class Persona
      uses Frances + (Italiano - :saludo)
    end

    juan = Persona.new
    expect(juan.saludo).to eq("Alo")
    expect(juan.despedida).to eq("Ciao")
  end

  it ('Resta selectores con una clase tira error si no existe el metodo') do
    expect {
      class Persona
        uses Frances + (Italiano - :sumar)
      end }.to raise_error(StandardError)
  end

  it ('Renombrar selectores') do
    Trait.define do
      name :Trabajo
      method :telefono do
        "1"
      end
      method :celular do
        "2"
      end
    end

    Trait.define do
      name :Particular
      method :celular do
        "3"
      end
    end


    class ConAlias
      uses Trabajo + (Particular << (:celular >> :celular_particular))
    end

    juan_con_alias = ConAlias.new
    expect(juan_con_alias.telefono).to eq("1")
    expect(juan_con_alias.celular).to eq("2")
    expect(juan_con_alias.celular_particular).to eq("3")
  end
end