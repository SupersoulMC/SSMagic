package hell.supersoul.magic.rpg;

import java.util.Arrays;

public class ElementManager {
	
	public enum Element {
		FIRE, ICE, EARTH, LIGHTNING, WATER, LIGHT, DARK, WIND;
	}
	
	public enum ElementComparison {
		NEUTRAL, EFFECTIVE, INEFFECTIVE;
	}
	
	public static ElementComparison compareElement(Element base, Element opponent) {
		
		if (base == null)
			return null;
		if (opponent == null)
			return null;
		
		switch (base) {
		case FIRE:
			if (Arrays.asList(Element.ICE).contains(opponent))
				return ElementComparison.EFFECTIVE;
			else if (Arrays.asList(Element.WATER).contains(opponent))
				return ElementComparison.INEFFECTIVE;
			else
				return ElementComparison.NEUTRAL;
		case ICE:
			if (Arrays.asList(Element.EARTH).contains(opponent))
				return ElementComparison.EFFECTIVE;
			else if (Arrays.asList(Element.FIRE).contains(opponent))
				return ElementComparison.INEFFECTIVE;
			else
				return ElementComparison.NEUTRAL;
		case DARK:
			if (Arrays.asList(Element.LIGHT).contains(opponent))
				return ElementComparison.EFFECTIVE;
			else
				return ElementComparison.NEUTRAL;
		case EARTH:
			if (Arrays.asList(Element.WIND).contains(opponent))
				return ElementComparison.EFFECTIVE;
			else if (Arrays.asList(Element.ICE).contains(opponent))
				return ElementComparison.INEFFECTIVE;
			else
				return ElementComparison.NEUTRAL;
		case LIGHT:
			if (Arrays.asList(Element.DARK).contains(opponent))
				return ElementComparison.EFFECTIVE;
			else
				return ElementComparison.NEUTRAL;
		case LIGHTNING:
			if (Arrays.asList(Element.WATER).contains(opponent))
				return ElementComparison.EFFECTIVE;
			else if (Arrays.asList(Element.WIND).contains(opponent))
				return ElementComparison.INEFFECTIVE;
			else
				return ElementComparison.NEUTRAL;
		case WATER:
			if (Arrays.asList(Element.FIRE).contains(opponent))
				return ElementComparison.EFFECTIVE;
			else if (Arrays.asList(Element.LIGHTNING).contains(opponent))
				return ElementComparison.INEFFECTIVE;
			else
				return ElementComparison.NEUTRAL;
		case WIND:
			if (Arrays.asList(Element.LIGHTNING).contains(opponent))
				return ElementComparison.EFFECTIVE;
			else if (Arrays.asList(Element.EARTH).contains(opponent))
				return ElementComparison.INEFFECTIVE;
			else
				return ElementComparison.NEUTRAL;
		}
		return null;
	}
}
