```


list.stream().collect(Collectors.groupingBy(Entity::getName)).values().stream()
                    .map(Entitys ->Entitys.stream()
                            .max(Comparator.comparing(Entity::getId)).get())
                    .collect(Collectors.toList())
                    
```
