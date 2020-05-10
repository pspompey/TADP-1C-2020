# Ventajas: no es necesario instanciar ninguna clase en particular, solo ejecutar el metodo correspondiente a la estrategia
#
#
# Desventajas: hay que definir por cada suma la estrategia a usar, aunque hay una default que es resolucion basico
#             en caso de no existir la estrategia habra un error de no method
#


module Estrategia
  def resolucion_basica(trait_que_llama, trait_para_sumar, *args)

    # en caso de coincidir alguno en nombre, el cuerpo del metodo sera una excepcion
    metodos_para_agregar = trait_que_llama.trait_methods.merge(trait_para_sumar.trait_methods) { |nombre_metodo| proc { raise MetodosDeIgualNombreException.new } }

  end

  def ejecutar_todo(trait_que_llama, trait_para_sumar, *args)
    metodos_para_agregar = trait_que_llama.trait_methods.merge(trait_para_sumar.trait_methods) do |nombre_metodo|
      proc {
        resultados = []
        resultados << trait_que_llama.trait_methods.fetch(nombre_metodo).call
        resultados << trait_para_sumar.trait_methods.fetch(nombre_metodo).call
      }
    end
  end

  def aplicar_foldeo(trait_que_llama, trait_para_sumar, *args)
    metodos_para_agregar = trait_que_llama.trait_methods.merge(trait_para_sumar.trait_methods) do |nombre_metodo|
      proc {
        resultados = []
        resultados << trait_que_llama.trait_methods.fetch(nombre_metodo).call
        resultados << trait_para_sumar.trait_methods.fetch(nombre_metodo).call
        resultados.inject { |acumulado, siguiente| acumulado.send(args[0], siguiente) }
      }
    end
  end

  def llamar_con_condicion(trait_que_llama, trait_para_sumar, *args)
    metodos_para_agregar = trait_que_llama.trait_methods.merge(trait_para_sumar.trait_methods) do |nombre_metodo|
      proc {
        resultado_primer_trait = trait_que_llama.trait_methods.fetch(nombre_metodo).call
        resultado_segundo_trait = trait_para_sumar.trait_methods.fetch(nombre_metodo).call
        if (resultado_primer_trait.send(args[0], resultado_segundo_trait))
          resultado_primer_trait
        else
          resultado_segundo_trait
        end
      }

    end
  end

  def definir_estrategia(nombre_estrategia, &bloque)
    define_singleton_method(nombre_estrategia, &bloque)
  end
end