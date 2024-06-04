package treehouse.server.api.reaction.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import treehouse.server.api.reaction.persistence.ReactionRepository;
import treehouse.server.global.annotations.Adapter;
import treehouse.server.global.entity.reaction.Reaction;

@Adapter
@RequiredArgsConstructor
@Slf4j
public class ReactionCommandAdapter {

    private final ReactionRepository reactionRepository;

    public Reaction saveReaction(Reaction reaction){
        return reactionRepository.save(reaction);
    }
}
