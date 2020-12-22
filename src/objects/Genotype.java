package objects;

import map.MapDirection;

import java.util.Random;

public class Genotype {
    private int[] GeneArr = {0,0,0,0,0,0,0,0};
    private Random generator = new Random();
    private int genesNum;


    public int[] getGeneArr() {
        return GeneArr;
    }

    public Genotype(int nGenes){
        genesNum = nGenes;
        for(int i=0; i < genesNum; i++){
            GeneArr[generator.nextInt(8)]++;
        }
        validation();
    }

    private void validation(){
        for(int i=0; i<8; i++){
            while(GeneArr[i] == 0){
                int x = generator.nextInt(8);
                if(GeneArr[x] > 1) {
                    GeneArr[x]--;
                    GeneArr[i]++;
                }
            }
        }
    }

    public MapDirection getNextMove(){
        int n = generator.nextInt(genesNum) + 1;
        int GenIdx = 0;
        while(true){
            if(n - this.GeneArr[GenIdx] > 0){
                n-=this.GeneArr[GenIdx];
                GenIdx++;
            }
            else{
                return MapDirection.values()[GenIdx];
            }
        }
    }

    private int getIGenFromGenotype(Genotype gen, int i){
        int n = i;
        int GenIdx =0;
        while(n > 0){
            if(n - GeneArr[GenIdx] > 0){
                n-=GeneArr[GenIdx];
                GenIdx++;
            }
            else{
                return GenIdx;
            }
        }
        return GenIdx;
    }

    public Genotype getGenotypeForChild(Genotype genB){
        Genotype result = new Genotype(this.genesNum);
        result.GeneArr = new int[]{0,0,0,0,0,0,0,0};
        int point1 = generator.nextInt(this.genesNum / 2)+1;
        int point2 = generator.nextInt(this.genesNum / 2)+1 + genesNum/2;
        for(int i = 0; i < this.genesNum; i++){
            if(i > point1 && i < point2){
                result.GeneArr[getIGenFromGenotype(genB, i)]++;
            }
            else{
                result.GeneArr[getIGenFromGenotype(this, i)]++;
            }
        }
        result.validation();
        return result;
    }
}
