class Wheel:  
    __gui = None  

    def __init__(self, gui):  
        self.__gui = gui

    def roll(self, leftVelocity, rightVelocity):  
        x = "x: "  + leftVelocity
        y = "y: "  + rightVelocity
        print x
        print y