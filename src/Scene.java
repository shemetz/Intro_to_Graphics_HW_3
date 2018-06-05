import java.util.ArrayList;
import java.util.List;

class Scene {
    //General settings / singletons
    public Color backgroundColor;
    public int shadowRayCount;
    public int maximumRecursionCount;
    public int superSamplingLevel;

    // Things

    public List<Material> materials;
    public List<Light> lights;
    public List<Shape> shapes;

    public Scene() {
        materials = new ArrayList<>();
        lights = new ArrayList<>();
        shapes = new ArrayList<>();
    }

    public Intersection raycast(Vec3 point, Vec3 direction) {
        Intersection closestIntersection = null;
        double min_dist_sqr = Integer.MAX_VALUE;
        for (Shape shape : shapes) {
            Intersection intersection = shape.findRayIntersection(point, direction);
            if (intersection != null) {
                double dist_sqr = point.minus(intersection.position).squareMagnitude();
                if (dist_sqr < min_dist_sqr) {
                    min_dist_sqr = dist_sqr;
                    closestIntersection = intersection;
                }
            }
        }
        return closestIntersection;
    }

    /**
     * @param materialIndex 1-based index
     * @return material with this index (from 0-based materials list)
     */
    public Material getMaterial(int materialIndex) {
        return materials.get(materialIndex - 1);
    }

    public Color getColor(Intersection hit) {
        if (hit == null)
            return backgroundColor;
        Material mat = getMaterial(hit.materialIndex);
        Color color = backgroundColor.scaledBy(mat.transparency).plus((mat.diffuseColor.plus(mat.specularColor)).scaledBy(1 - mat.transparency));
        //ambient* mat.getDiffuse();
        for (Light light : lights) {
            //todo
//			color=color.plus(mat.getShade());
//			(ray, hit, L->getDir(), L->getColor());
        }
        return color;
    }

//    public Color getColor(Intersection hit) {
//        if (hit == null)
//            return backgroundColor;
//
//        Material mat = getMaterial(hit.materialIndex);
//        Color color = new Color(0, 0, 0);
//
//        //ambient lighting
//        color = color.plus(backgroundColor);
//
//        Vec3 point = hit.position;
//        for (Light light : lights) {
//            Vec3 direction = light.position.minus(point).normalized();
//            Vec3 start = point.plus(direction.scaledBy(1.001));
//            boolean lightReaches = true;
//            for (Shape s : shapes) {
//                Intersection shadowHit = s.findRayIntersection(start, direction);
//                if (shadowHit != null) {
//                    lightReaches = false;
//                    break; //TODO transparency here?
//                }
//            }
//            if (lightReaches) {
//                //diffuse lighting
//                color = color.plus(mat.diffuseColor.scaledBy(hit.normal.dot(direction)));
//                //specular lighting
//            }
//        }
//        return color;
//    }

}
