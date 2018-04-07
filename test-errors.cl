class Incorrect1 {
   a
};

class Incorrect2{
    a:String;
    d;
    b:String <- *;

    hi():Object{
        {
            2^@345!@5mm;
            "red";
            ehh?;
        }
    };
    g:String;
};
class Correct1 {
    name:String;
    color:String <- "Blue";

    test():Object{
       "hi"
    };
};
class Correct2{
    name:String;
    id:Int <- 1;
};
class Incorrect3{
    name:String<-4;
    %$@#$%;
};
class LetGood inherits IO{
    correctLet():Object{
        {
            let bob:String,joe:String<-"Joesph",bob:Cat<-"Joesph" in{
                out_string("hi");
            };
            
        }
    };
};
class Incorrect4{
    name:String<-4;
    %$@#$%;
};
class LetBad inherits IO{
    correctLet():Object{
        {
            let bob:String,joe:String<-*,bob:Cat<-"hi",bad:Cat<-!#$,good:String<-"HI" in{
                out_string("hi");
            };
            out_string("should be recognized");
            %$@#$%;
            
        }
    };
};