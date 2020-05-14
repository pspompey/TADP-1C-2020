require '../lib/testing_framework'
require_relative '../src/Trait'
require_relative '../src/Class'
require_relative '../src/Symbol'
require_relative '../src/conflict_resolution'

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
  method :metodo1 do
    "kawuabonga"
  end

  method :metodo2 do |un_numero|
    un_numero
  end

  method :metodo3 do
    "zaraza"
  end
end

class Conflicto
  uses MiTrait + MiOtroTrait
end

test_suite do
  obj = Conflicto.new
  conflicto_metodo1_raise_error = false
  test do
    test_suite = TestSuite.new do
      test do
      begin
        obj.metodo1
      rescue DuplicateMethodError => error
        conflicto_metodo1_raise_error = error.message == "Conflicto con el metodo metodo1"
      end
      end
    end

    test_suite.ejecutar

    assert conflicto_metodo1_raise_error
  end

  conflicto_metodo2_raise_error = false
  test do
    test_suite = TestSuite.new do
      test do
        begin
          obj.metodo2
        rescue DuplicateMethodError => error
          conflicto_metodo2_raise_error = error.message == "Conflicto con el metodo metodo2"
        end
      end
    end

    test_suite.ejecutar

    assert conflicto_metodo2_raise_error
  end

  ejecuto_metodo3 = false
  test do
    test_suite = TestSuite.new do
      test { ejecuto_metodo3 = obj.metodo3 == "zaraza" }
    end

    test_suite.ejecutar

    assert ejecuto_metodo3
  end


end
