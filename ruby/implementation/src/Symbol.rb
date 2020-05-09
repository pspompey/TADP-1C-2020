class Symbol
  def >>(sym_alias)
    argumentos = self.to_s << " >> " << sym_alias.to_s
    argumentos_separados = argumentos.split(" ")
    lista_renombre = [argumentos_separados[0].to_sym,argumentos_separados[2].to_sym]
  end
end
