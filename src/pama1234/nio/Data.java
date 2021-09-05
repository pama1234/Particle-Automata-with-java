package pama1234.nio;

public interface Data<D>{
  D toData();
  void fromData(D in);
}