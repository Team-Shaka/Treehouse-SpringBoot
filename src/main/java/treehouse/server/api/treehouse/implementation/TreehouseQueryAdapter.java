package treehouse.server.api.treehouse.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import treehouse.server.api.treehouse.persistence.TreehouseRepository;
import treehouse.server.global.annotations.Adapter;
import treehouse.server.global.entity.treeHouse.TreeHouse;
import treehouse.server.global.exception.GlobalErrorCode;
import treehouse.server.global.exception.ThrowClass.TreehouseException;

@Adapter
@RequiredArgsConstructor
@Slf4j
public class TreehouseQueryAdapter {

    private final TreehouseRepository treehouseRepository;

    public TreeHouse getTreehouseById(Long treehouseId){
        return treehouseRepository.findById(treehouseId).orElseThrow(()->new TreehouseException(GlobalErrorCode.TREEHOUSE_NOT_FOUND));
    }

    public boolean isTreehouseNameAvailable(String name) {
        return !treehouseRepository.existsByName(name);
    }
}
