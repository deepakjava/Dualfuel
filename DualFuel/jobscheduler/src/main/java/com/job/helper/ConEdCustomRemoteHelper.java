package com.job.helper;


import com.dto.PlutoCustomer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ConEdCustomRemoteHelper extends ConEdRemoteHelper{
    private List<PlutoCustomer> data = null;

    public ConEdCustomRemoteHelper(List<PlutoCustomer> data) {
        this.data = data;
    }

    public List<PlutoCustomer> filterData( List<PlutoCustomer> data){
        return this.data ;
    }

}
