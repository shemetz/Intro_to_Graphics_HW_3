public class Plane extends Shape
{
    final Vec3 normal;
    final double offset;
    
    public Plane(Vec3 normal, double offset, int materialIndex)
    {
        super(materialIndex);
        this.normal = normal.normalized();
        this.offset = offset;
    }
    
    public String toString()
    {
        return "Plane(" + normal + ", " + offset + ", " + materialIndex + ")";
    }
    
    @Override
    public Intersection findRayIntersection(Vec3 origin, Vec3 direction)
    {
        double dirDotNorm = direction.dot(normal);
        if (Math.abs(dirDotNorm) < 0.01)
            return null; // plane is parallel to ray
        double t = (offset - origin.dot(normal)) / dirDotNorm;
        if (t <= 0)
            return null; // plane is behind origin
        if (t == Double.NaN)
            return null; // plane is behind origin
        Vec3 intersection_position = origin.plus(direction.scaledBy(t));
        //TODO maybe take opposite direction normal in certain cases?
        return new Intersection(intersection_position, normal, materialIndex);
    }
}
