const sampler_t sampler = CLK_NORMALIZED_COORDS_FALSE | CLK_ADDRESS_CLAMP | CLK_FILTER_NEAREST;

__kernel void drawImage(__write_only image2d_t dst_image, __read_only image2d_t src_image, float xoff, float yoff)
{
    const int x = get_global_id(0);
	const int y = get_global_id(1);

    int2 in_coords = (int2) { x, y };

    uint4 pixel = read_imageui(src_image, sampler, in_coords);
    //pixel = -16184301;
    printf("%d, %d\n", pixel.x, xoff);

    //printf("%d, %d, %u\n", x, y, pixel);

    const int sx = get_global_size(0);
    const int sy = get_global_size(1);

    int2 out_coords = (int2) { ((int) xoff + x) % sx, ((int) yoff + y) % sy};
    
    //int2 out_coords = (int2) { xoff + x, yoff + y};
    write_imageui(dst_image, out_coords, pixel);
}