require '../lib/testing_framework'
require '../src/Trait'
require '../src/Class'
require '../src/Symbol'
require '../src/conflict_resolution'


Trait.define do
  name :MiTrait
  method :metodo1 do
    "Hola"
  end
  method :metodo2 do |un_numero|
    un_numero * 0 + 42
  end
  method :metodo3 do
    "Scorpion"
  end
end

Trait.define do
  name :MiOtroTrait
  method :metodo2 do|un_numero|
    un_numero / 1 + 0
  end
  method :metodo3 do
    "SubZero"
  end
end


class Conflicto
  uses MiTrait + (MiOtroTrait <= {:metodo2 => ConflictResolutionExecAll.new,
                                  :metodo3 => ConflictResolutionExecIf.new(condition: Proc.new { |resultado| resultado == "Liu Kang" },
                                                                           option: Proc.new { "Finish Him!!" }
                                  )})
end

test_suite do
  o = Conflicto.new

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
      test { comprendo_metodo3 = o.metodo3 == "Finish Him!!" }
    end

    test_suite.ejecutar

    assert comprendo_metodo3
  end
end