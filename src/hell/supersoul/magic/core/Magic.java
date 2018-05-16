package hell.supersoul.magic.core;

public class Magic {

    protected int level = 0;
    protected int maxLevel = -1;

    public Magic(int level) {
        this.level = level;
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (obj == null)
    		return false;
    	return (obj.getClass().equals(this.getClass()));
    }

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getMaxLevel() {
		return maxLevel;
	}

}
