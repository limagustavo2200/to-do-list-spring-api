package com.gustavo.todo_list.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {
    

    public static void copyNonNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }


    // Método que retorna os nomes das propriedades nulas de um objeto
    public static String[] getNullPropertyNames(Object source) {
        // Cria um BeanWrapper para acessar dinamicamente as propriedades do objeto "source"
        final BeanWrapper src = new BeanWrapperImpl(source);

        // Obtém todos os descritores de propriedades do objeto "source"
        // Cada PropertyDescriptor contém informações sobre o nome, tipo e métodos da propriedade
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        
        // Lista de nomes vazios com HashSet para garantir que não haverá duplicatas, mas não garante a ordenação.
        Set<String> emptyNames = new HashSet<>(); 

        for(PropertyDescriptor pd: pds) {
            Object srcValue = src.getPropertyValue(pd.getName());

            // pegando o nome de todas as propriedades que o valor for null e jogando no emptyName
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);

    }
}
