class Main inherits IO{
    main():Object{
        "hi"
    };
};
class Animal{

};
class Dog inherits Animal{

};
class Cat inherits Object{

};
class GoldenRetriever inherits Dog{

};
class LightGolden inherits GoldenRetriever{

};
class SmallCat inherits Cat{
    name: String;
};