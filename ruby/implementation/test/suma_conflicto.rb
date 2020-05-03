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

Trait.define do
  name :MiOtroTrait
  method :metodo1 do
    "kawuabonga"
  end
  method :metodo3 do
    "zaraza"
  end
end


test_suite do
  conflicto_metodo_raise_error = false
  test do
    test_suite = TestSuite.new do
      test do
      begin
        class Conflicto
          uses MiTrait + MiOtroTrait
        end
      rescue DuplicateMethodError => error
        conflicto_metodo_raise_error = error.message == "Conflicto con el metodo metodo1"
      end
      end
    end

    test_suite.ejecutar

    assert conflicto_metodo_raise_error
  end

end
