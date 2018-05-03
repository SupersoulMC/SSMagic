package hell.supersoul.magic.core.regular;

import hell.supersoul.magic.core.RegularM;

public enum RegularMEnum {

    Blizzard {
        @Override
        public RegularM get(Integer level) {
            return new Blizzard(level);
        }
    },

    Fire {
        @Override
        public RegularM get(Integer level) {
            return new Fire(level);
        }
    },

    Lightning {
        @Override
        public RegularM get(Integer level) {
            return new Lightning(level);
        }
    },

    Water {
        @Override
        public RegularM get(Integer level) {
            return new Water(level);
        }
    };

    public abstract RegularM get(Integer level);

}
