package ${basePackage}.service.impl;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import ${basePackage}.model.${bigClassName};
import ${basePackage}.mapper.${bigClassName}Mapper;
import ${basePackage}.service.I${bigClassName}Service;


@Service
public class ${bigClassName}ServiceImpl implements I${bigClassName}Service {

@Autowired
private ${bigClassName}Mapper ${smallClassName}Mapper;

@Override
public void insert(${bigClassName} ${smallClassName}) {

${smallClassName}Mapper.insert(${smallClassName});
}

@Override
public void delete(${bigClassName} ${smallClassName}){

${smallClassName}Mapper.delete(${smallClassName});
}

@Override
public void update(${bigClassName} ${smallClassName}) {
this.${smallClassName}Mapper.update(${smallClassName});
}


@Override
public ${bigClassName} select(${bigClassName} ${smallClassName}) {
return this.${smallClassName}Mapper.select(${smallClassName});
}
}