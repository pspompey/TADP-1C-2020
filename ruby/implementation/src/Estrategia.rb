module Estrategia
  def resolucion_basica(trait_que_llama, trait_para_sumar)

    # en caso de coincidir alguno en nombre, el cuerpo del metodo sera una excepcion
    metodos_para_agregar = trait_que_llama.trait_methods.merge(trait_para_sumar.trait_methods) { |nombre_metodo| proc { raise MetodosDeIgualNombreException.new } }

  end

  def ejecutar_todo(trait_que_llama, trait_para_sumar)
    metodos_para_agregar = trait_que_llama.trait_methods.merge(trait_para_sumar.trait_methods) do |nombre_metodo|
      proc {
        resultados = []
        resultados << trait_que_llama.trait_methods.fetch(nombre_metodo).call
        resultados << trait_para_sumar.trait_methods.fetch(nombre_metodo).call

      }

    end
  end
end
