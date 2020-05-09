require '../lib/testing_framework'
require '../src/Trait'
require '../src/Class'
require '../src/Symbol'
require '../src/conflict_resolution'

Trait.define do
  name :MiTrait
  method :metodo1 do |un_numero|
    un_numero * 1 + 1
  end
end

Trait.define do
  name :MiOtroTrait
  method :metodo1 do|un_numero|
    un_numero / 1 + 1
  end
end

class ConflictoCustom
  uses MiTrait + (MiOtroTrait <= ConflictResolutionCustom.new(
      function: proc {
        "hola"
      }
  ))
end

class ConflictoCustomCurrentResult
  uses MiTrait + (MiOtroTrait <= ConflictResolutionCustom.new(
      function: proc { |current_method, _, *args|
        current_method.call(*args) * 2
      }
  ))
end

class ConflictoCustomResults
  uses MiTrait + (MiOtroTrait <= ConflictResolutionCustom.new(
      function: proc { |current_method, other_trait_method, *args|
        current_method.call(*args) *  other_trait_method.call(*args)
      }
  ))
end

class ConflictoCustomResultsArgs
  uses MiTrait + (MiOtroTrait <= ConflictResolutionCustom.new(
      function: proc { |current_method, other_trait_method, args|
        current_method.call(*args) *  other_trait_method.call(*args) + args
      }
  ))
end

class ConflictoCustomExecIf
  uses MiTrait + (MiOtroTrait <= ConflictResolutionCustom.new(
      function: proc { |current_method, other_trait_method, *args|
        if (current_method.call(*args) == 2)
          current_method.call(*args)
        elsif (other_trait_method.call(*args) == 3)
          other_trait_method.call(*args)
        else
          raise StandardError, "No cumple ninguna condición"
        end
      }
  ))
end

test_suite do

  test do
    custom = ConflictoCustom.new
    ejecuto_lo_que_quiera = false
    test_suite = TestSuite.new do
      test { ejecuto_lo_que_quiera = custom.metodo1(33) == "hola"}
    end

    test_suite.ejecutar

    assert ejecuto_lo_que_quiera
  end


  test do
    custom_current_result = ConflictoCustomCurrentResult.new
    ejecuto_resultado_propio = false
    test_suite = TestSuite.new do
      test { ejecuto_resultado_propio = custom_current_result.metodo1(33) == 68}
    end

    test_suite.ejecutar

    assert ejecuto_resultado_propio
  end


  test do
    custom_results = ConflictoCustomResults.new
    ejecuto_resultados = false
    test_suite = TestSuite.new do
      test { ejecuto_resultados = custom_results.metodo1(1) == 4}
    end

    test_suite.ejecutar

    assert ejecuto_resultados
  end

  test do
    custom_results_args = ConflictoCustomResultsArgs.new
    ejecuto_resultados_argumento = false
    test_suite = TestSuite.new do
      test { ejecuto_resultados_argumento = custom_results_args.metodo1(1) == 5}
    end

    test_suite.ejecutar

    assert ejecuto_resultados_argumento
  end

  custom_exec_if = ConflictoCustomExecIf.new

  test do
    ejecuto_segun_condicion = false
    test_suite = TestSuite.new do
      test { ejecuto_segun_condicion = custom_exec_if.metodo1(1) == 2}
    end

    test_suite.ejecutar

    assert ejecuto_segun_condicion
  end

  test do
    ejecuto_segun_condicion = false
    test_suite = TestSuite.new do
      test { ejecuto_segun_condicion = custom_exec_if.metodo1(2) == 3}
    end

    test_suite.ejecutar

    assert ejecuto_segun_condicion
  end

  test do
    ejecuto_segun_condicion = false
    test_suite = TestSuite.new do
      test do
        begin
          custom_exec_if.metodo1(10)
        rescue StandardError => error
          ejecuto_segun_condicion = error.message == "No cumple ninguna condición"
        end
      end
    end

    test_suite.ejecutar

    assert ejecuto_segun_condicion
  end

end


