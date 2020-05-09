require '../lib/testing_framework'
require '../src/Trait'
require '../src/Class'
require '../src/Symbol'
require '../src/conflict_handler'

Trait.define do
  name :MiTrait
  method :metodo1 do |un_numero|
    un_numero * 0 + 42
  end
end

Trait.define do
  name :MiOtroTrait
  method :metodo1 do|un_numero|
    un_numero / 1 + 0
  end
end

class ConflictoExecIfOption
  uses MiTrait + (MiOtroTrait <= ConflictResolution.exec_if(
      condition: Proc.new { |resultado| resultado > 1000 },
      option: Proc.new { |resultado| resultado + 3 }))
end

class ConflictoExecIfFirst
  uses MiTrait + (MiOtroTrait <= ConflictResolution.exec_if(
      condition: Proc.new { |resultado| resultado > 0 },
      option: Proc.new { |resultado| resultado + 3 }))
end

class ConflictoExecIfSecond
  uses MiTrait + (MiOtroTrait <= ConflictResolution.exec_if(
      condition: Proc.new { |resultado| resultado < 40 },
      option: Proc.new { |resultado| resultado + 3 }))
end


test_suite do
  opcion = ConflictoExecIfOption.new

  test do
    ejecuto_opcion = false
    test_suite = TestSuite.new do
      test { ejecuto_opcion = opcion.metodo1(33) == 36}
    end

    test_suite.ejecutar

    assert ejecuto_opcion
  end

  primero = ConflictoExecIfFirst.new

  test do
    ejecuto_primero = false
    test_suite = TestSuite.new do
      test { ejecuto_primero = primero.metodo1(33) == 42}
    end

    test_suite.ejecutar

    assert ejecuto_primero
  end

  segundo = ConflictoExecIfSecond.new

  test do
    ejecuto_segundo = false
    test_suite = TestSuite.new do
      test { ejecuto_segundo = segundo.metodo1(33) == 33}
    end

    test_suite.ejecutar

    assert ejecuto_segundo
  end
end

