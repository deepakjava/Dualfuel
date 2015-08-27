package com.job.helper;

import com.dto.PlutoCustomer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ConEdQueensRemoteHelper extends ConEdRemoteHelper{

    public List<PlutoCustomer> filterData( List<PlutoCustomer> data){
        return data.stream().filter(p -> p.getBorough().equalsIgnoreCase("QN")).map(Function.<PlutoCustomer>identity()).collect(Collectors.toCollection(ArrayList::new));
    }
}
