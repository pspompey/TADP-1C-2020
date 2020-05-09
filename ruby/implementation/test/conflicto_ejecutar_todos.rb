require '../lib/testing_framework'
require '../src/Trait'
require '../src/Class'
require '../src/Symbol'
require '../src/conflict_handler'


Trait.define do
  name :MiTrait
  method :metodo1 do
    "Hola"
  end
  method :metodo2 do |un_numero|
    un_numero * 0 + 42
  end
end

Trait.define do
  name :MiOtroTrait
  method :metodo2 do|un_numero|
    un_numero / 1 + 0
  end
  method :metodo3 do
    "zaraza"
  end
end

class ConflictoAll
  uses MiTrait + (MiOtroTrait <= ConflictResolution.exec_all())
end

test_suite do
  o = ConflictoAll.new

  test do
    comprendo_metodo1 = false
    test_suite = TestSuite.new do
      test { comprendo_metodo1 = o.metodo1 == "Hola" }
    end

    test_suite.ejecutar

    assert comprendo_metodo1
  end

  test do
    comprendo_metodo2 = false
    test_suite = TestSuite.new do
      test { comprendo_metodo2 = (o.metodo2(33)[0] == 42 && o.metodo2(33)[1] == 33)}
    end

    test_suite.ejecutar

    assert comprendo_metodo2
  end

  test do
    comprendo_metodo3 = false
    test_suite = TestSuite.new do
      test { comprendo_metodo3 = o.metodo3 == "zaraza" }
    end

    test_suite.ejecutar

    assert comprendo_metodo3
  end
end