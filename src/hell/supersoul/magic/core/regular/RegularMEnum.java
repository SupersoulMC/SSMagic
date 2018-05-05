package hell.supersoul.magic.core.regular;

import hell.supersoul.magic.core.RegularM;

public enum RegularMEnum {

    BLIZZARD {
        @Override
        public RegularM get(Integer level) {
            return new Blizzard(level);
        }
    },

    FIRE {
        @Override
        public RegularM get(Integer level) {
            return new Fire(level);
        }
    },

    LIGHTNING {
        @Override
        public RegularM get(Integer level) {
            return new Lightning(level);
        }
    },

    WATER {
        @Override
        public RegularM get(Integer level) {
            return new Water(level);
        }
    };

    public abstract RegularM get(Integer level);

}
