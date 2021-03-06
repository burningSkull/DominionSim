package be.aga.dominionSimulator.cards;

import java.util.ArrayList;
import java.util.Collections;

import be.aga.dominionSimulator.DomCard;
import be.aga.dominionSimulator.enums.DomCardName;

public class IslandCard extends DomCard {
    public IslandCard () {
      super( DomCardName.Island);
    }

    public void play() {
        if (!owner.getCardsFromPlay(DomCardName.Island).isEmpty()) {
            //this is possible if card was throne roomed or king's courted
            owner.moveToIslandMat(owner.removeCardFromPlay(this));
        }
        if (owner.getCardsInHand().isEmpty())
            return;
        if (owner.isHumanOrPossessedByHuman()) {
            ArrayList<DomCardName> theChooseFrom=new ArrayList<DomCardName>();
            for (DomCard theCard : owner.getCardsInHand()) {
                theChooseFrom.add(theCard.getName());
            }
            DomCardName theChosenCard = owner.getEngine().getGameFrame().askToSelectOneCard("Trash a card", theChooseFrom, "Don't trash");
            owner.moveToIslandMat(owner.removeCardFromHand(owner.getCardsFromHand(theChosenCard).get(0)));
        } else {
            Collections.sort(owner.getCardsInHand(), SORT_FOR_DISCARDING);
            owner.moveToIslandMat(owner.removeCardFromHand(owner.getCardsInHand().get(0)));
        }
    }

    public boolean wantsToBePlayed() {
      for (DomCard theCard : owner.getCardsInHand()) {
        if (theCard!=this && theCard.getDiscardPriority(1)<16){
          return true;
        }
      }
      //if it's the only card left in hand, play it
      return owner.getCardsInHand().size()<=1;
    }
}