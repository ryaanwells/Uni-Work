function f = sineplot(xspacing);
x = [0: xspacing: 2*pi];
y = sin(x);
f = figure();
plot(x, y);
