class Main inherits IO {
   main(): SELF_TYPE {
    {
        out_string("hello\n");
         d@Dog.test();
         d.test();
         d.test("hello");
         x <- new Dog;
         if isvoid dog then d.test() else d.eat() fi;
        isvoid dog;
        if isvoid dog then dog.bark() else out_string("no dog") fi;
        dog+cat;
        dog-cat;
        dog*cat;
        dog/cat;
        d+c*a-r/w;
        ~dog;
        dog< cat;
        cat <= dog;
        cat = dog;
        not dog;
        (d).speak();
        5;
        3;
        4+3;
        3/2;
        if true then dog.bark() else {"do nothing";} fi;
        if false then dog.bark() else {"do nothing";} fi;
        tRUE;
        fAlse;

        case dog of 
            gr:GoldenRet => out_string("golden retriver");
        esac;

        case dog of 
            gr:GoldenRet => out_string("golden retriver");
            dh:Daschound => out_string("dashound");
            l:Labrador => out_string("labrador");
        esac;

        let bob:String,joe:String<-"Joesph",bob:Cat<-"Joesph" in{
            bob <- "Hello";
            out_string(bob);
        };

        if bob then {
            yell("bob");
        }else {
            if joe then{
                yell("Asdfasdf");
            }else{
                while x<4 loop{
                    x<-x+1;
                } pool;
            }
            fi;
        }
        fi;
        x<-3;
        if da then "hello4" else "goodbye" fi;
        -- if asdfas d
        -- z<- new !@#$;
        -- 5+4;
        -- let x,y=3 in {
        --     @#$%@#$%d
        -- };
        -- z<-%;
        

    }
   };
   eat(d:Dog):Object {
      let x:Dog in {
           "nothing";
      }
   };
   eat2(d:Dog,c:Dog):Object {
       {
            d@Dog.test();
            d.test();
            d.test("hello");
            x <- new Dog;
       }
       
   };
};
class Dog {
    name:String;
    color:String <- "Blue";

    test():Object{
        name <- "Buddy"
    };
};
class Empty {
   
};