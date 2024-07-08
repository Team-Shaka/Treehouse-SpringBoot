package treehouse.server.api.treehouse.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import treehouse.server.api.treehouse.persistence.TreehouseRepository;
import treehouse.server.global.annotations.Adapter;
import treehouse.server.global.entity.treeHouse.TreeHouse;

@Adapter
@RequiredArgsConstructor
@Slf4j
public class TreehouseCommandAdapter {

    private final TreehouseRepository treehouseRepository;


    public TreeHouse saveTreehouse(TreeHouse treehouse) {
        return treehouseRepository.save(treehouse);
    }
}
