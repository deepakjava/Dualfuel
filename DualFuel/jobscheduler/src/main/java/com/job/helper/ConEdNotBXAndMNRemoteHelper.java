package com.job.helper;


import com.dto.PlutoCustomer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ConEdNotBXAndMNRemoteHelper extends ConEdRemoteHelper{

    public List<PlutoCustomer> filterData( List<PlutoCustomer> data){
        return data.stream().filter(p -> !(p.getBorough().equalsIgnoreCase("QN") || p.getBorough().equalsIgnoreCase("MN") || p.getBorough().equalsIgnoreCase("BX"))).map(Function.<PlutoCustomer>identity()).collect(Collectors.toCollection(ArrayList::new));
    }

}
