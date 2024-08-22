package com.reinan.demo_park_api.common;

import com.reinan.demo_park_api.entity.Client;
import com.reinan.demo_park_api.repository.projection.ClientProjection;


import java.util.ArrayList;
import java.util.List;

public class ClientConstants {

    public static final Client CLIENT = new Client("John Lei", "95333012016");
    public static final Client CLIENT2 = new Client("Bob Smith", "98765432109");

    public static final List<ClientProjection> CLIENTS = List.of(
            new ClientProjection() {
                public Long getId() { return 1L; }
                public String getName() { return "John Lei"; }
                public String getCpf() { return "95333012016"; }
            },
            new ClientProjection() {
                public Long getId() { return 2L; }
                public String getName() { return "Bob Smith"; }
                public String getCpf() { return "98765432109"; }
            }

    );


}
