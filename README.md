# particles2D

Decisions:
- 3D or 2D? (how to use a 3D interface in java?)
- Starting conditions:
  - number of particles
  - mass distribution
  - speed distribution
  - angular momentum

Three steps highly paralelizable
- Calculate the acelerations
- Update the positions  x = x_0 + v + a/2
- Update speeds v = v_0 + a
- Do we keep the changes in the variation of aceleration?

>> Make colision?

GUI
- 


- Possible optimization: Do not use all the pairs of interations,
make something like space partitioning to reduce overall conditions


Class Particle:
-mass
-velocity
-aceleration

Class Vector2D:
int x, y;
