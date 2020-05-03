require 'colorize'

class Test
  def initialize(contenido)
    @contenido = contenido
  end

  def ejecutar
    @fallar = proc { return :fail }
    instance_eval(&@contenido)
    :pass
  end

  private

  def assert(un_booleano)
    @fallar.call unless un_booleano
  end

  def deny(un_booleano)
    assert !un_booleano
  end
end

class TestSuite
  def initialize(&definicion_de_la_suite)
    @tests = []
    self.instance_eval(&definicion_de_la_suite)
  end

  def test(&contenido_del_test)
    @tests << Test.new(contenido_del_test)
  end

  def ejecutar(notificar_resultados: false)
    @tests.each do |test|
      resultado = test.ejecutar
      mostrar_resultado(resultado) if notificar_resultados
    end
  end

  private

  def mostrar_resultado(resultado)
    if resultado == :fail
      puts "FAIL".red
    else
      puts "PASS".green
    end
  end
end

def test_suite(&definicion_test_suite)
  test_suite = TestSuite.new(&definicion_test_suite)
  test_suite.ejecutar(notificar_resultados: true)
end

