package com.example.bpflowjs;

import java.util.List;
import java.util.Map;

public interface DiagramRunStep{

        String get_diagram();

        Map<String, List<String>> get_tokens(String node);
}
