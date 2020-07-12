package to.klay.wspro.server.setup.finders.cards.wsengdb;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.server.setup.finders.cards.CardFinder;
import to.klay.wspro.server.setup.finders.cards.CardFinderDeckResult;
import to.klay.wspro.server.setup.modules.ServerOptions;
import ton.klay.wspro.core.api.cards.PaperCard;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * {@link CardFinder} that uses a local directory containing the
 *  <a href="https://github.com/CCondeluci/WeissSchwarz-ENG-DB"> WeissSchwarz-ENG-DB made by CCondeluci</a>
 */
public class WsEngDbCardFinder implements CardFinder {

    private static final Logger log = LogManager.getLogger();
    private final Path path;
    private  List<Path> searchableFiles;
    private final HashMap<String, Optional<PaperCard>> cardCache = new HashMap<>(100);
    private final HashSet<Path> fullyVisitedFiles = new HashSet<>(100);

    @Inject
    public WsEngDbCardFinder(@Named(ServerOptions.CARD_SOURCE_DIRECTORY_PATH_KEY) Path path){

        this.path = path;
    }

    @Override
    public Optional<PaperCard> getCard(String id) {
        if (cardCache.containsKey(id)){
            return cardCache.get(id);
        } else {
            Optional<PaperCard> card = getCardFromDB(id);
            cardCache.put(id, card);
            return card;
        }
    }

    private Optional<PaperCard> getCardFromDB(String id) {
        //we could do this intelligently, such as caching the files that
        // cards come from so we dont revisit files but thats a later issue.
        Gson gson = new Gson();
        final PaperCard[] foundCard = {null};

        //when we visit each file, serialize and look for our card, cache any other card we visit.
        FileVisitor<Path> fileVisitor = new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

                //check if we've already searched this file, if the card was in this current file,
                // it would've been cached already
                if (fullyVisitedFiles.contains(file)){
                    return FileVisitResult.CONTINUE;
                }

                log.debug("Searching File:" + file.toString());

                try {
                    WsEngDbCard[] wsEngDbCards = gson.fromJson(new FileReader(file.toFile()), WsEngDbCard[].class);
                    for (WsEngDbCard serializedCard : wsEngDbCards) {
                        WsEngDbCardPaperCardAdapter paperCard = WsEngDbCardPaperCardAdapter.adapt(serializedCard);
                        if (paperCard.getID().equalsIgnoreCase(id)) {
                            foundCard[0] = paperCard;
                            return FileVisitResult.TERMINATE;
                        } else {
                            cardCache.putIfAbsent(paperCard.getID(), Optional.of(paperCard));
                        }
                    }
                } catch (FileNotFoundException e) {
                    log.error(e);
                }
                //we visited every card in this file, mark it as cached and continue
                fullyVisitedFiles.add(file);
                return FileVisitResult.CONTINUE;
            }
        };


        //actually walk the DB now
        try {
            Files.walkFileTree(path, fileVisitor);

        } catch (IOException e) {
            log.error(e);
            return Optional.empty();
        }

        return Optional.ofNullable(foundCard[0]);
    }

    @Override
    public CardFinderDeckResult sourceDeck(List<String> cardIdList) {
        List<PaperCard> foundCards = new ArrayList<>();
        List<String> notFoundIds = new ArrayList<>();

        for (String cardId : cardIdList) {
            Optional<PaperCard> optionalCard = getCard(cardId);
            if (optionalCard.isPresent()){
                foundCards.add(optionalCard.get());
            } else {
                notFoundIds.add(cardId);
            }
        }

        return CardFinderDeckResult.create(foundCards, notFoundIds);

    }
}
