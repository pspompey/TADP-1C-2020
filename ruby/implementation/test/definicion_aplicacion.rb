require '../lib/testing_framework'
require '../src/Trait'
require '../src/Class'
require '../src/Symbol'
require '../src/conflict_handler'
require '../src/conflict_resolution'

Trait.define do
  name :MiTrait
  method :metodo1 do
    "Hola"
  end
  method :metodo2 do |un_numero|
    un_numero * 0 + 42
  end
end

class MiClase
  uses MiTrait
def metodo1
  "mundo"
end
end


test_suite do
  o = MiClase.new
  test do
    comprendo_metodo1 = false
    test_suite = TestSuite.new do
      test { comprendo_metodo1 = o.metodo1 == "mundo" }
    end

    test_suite.ejecutar

    assert comprendo_metodo1
  end

  test do
    comprendo_metodo2 = false
    test_suite = TestSuite.new do
      test { comprendo_metodo2 = o.metodo2(33) == 42 }
    end

    test_suite.ejecutar

    assert comprendo_metodo2
  end

end


