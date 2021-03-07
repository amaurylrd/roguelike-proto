package engine.render2d;

import static org.jocl.CL.*;
import org.jocl.*;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

//host code
public class OpenCL {
    private OpenCL() {}

    /**
     * The OpenCL context.
     */
    private static cl_context context;

    /**
     * The OpenCL command queue.
     */
    private static cl_command_queue commandQueue;

    /**
     * The OpenCL kernel.
     */
    private static cl_kernel kernel;

    /**
     * The OpenCL plateform.
     */
    private static cl_platform_id platform;

    /**
     * The OpenCl device.
     */
    private static cl_device_id device;
    
    /**
     * The program to run on the device.
     */
    private static cl_program program;

    /**
     * The C source code to compile.
     */
    private final static String source = "kernel/test.cl";

    /**
     * The name of the kernel function.
     */
    private final static String function = "drawImage"; //drawQuad fillQuad

    public static void setup() {
        setExceptionsEnabled(true);

        //obtain the number of platforms
        int numPlatforms[] = new int[1];
        clGetPlatformIDs(0, null, numPlatforms);

        //obtain the plateform ids
        cl_platform_id platforms[] = new cl_platform_id[numPlatforms[0]];
        clGetPlatformIDs(platforms.length, platforms, null);
        
        //take first plateform
        platform = platforms[0];

        //set the context properties for this plateform
        cl_context_properties contextProperties = new cl_context_properties();
        contextProperties.addProperty(CL_CONTEXT_PLATFORM, platform);

        //obtain the number of gpu for this platform
        int numDevices[] = new int[1];
        clGetDeviceIDs(platform, CL_DEVICE_TYPE_GPU, 0, null, numDevices);

        //private static 
        // final int GPU_INDEX = 0;
        // final int CPU_INDEX = 1;
        // final int DEVICE_INDEX = 0;
        // cl_device_id[][] devices = new cl_device_id[2][]; 
        // int deviceIndex = GPU_INDEX;

        // int numDevices[] = new int[1];
        // if (CL_DEVICE_NOT_FOUND == clGetDeviceIDs(platform, CL_DEVICE_TYPE_GPU, 0, null, numDevices)) {
        //     clGetDeviceIDs(platform, CL_DEVICE_TYPE_CPU, 0, null, numDevices);
        //     deviceIndex = CPU_INDEX;
        // }

        // devices[deviceIndex] = new cl_device_id[numDevices[0]];
        // for (int i = 0; i < devices[deviceIndex].length; ++i)
        //     clGetDeviceIDs(platform, CL_DEVICE_TYPE_GPU, devices[deviceIndex].length, devices[deviceIndex], null);
        // device = devices[deviceIndex][0];

        //récupérer les infos utilies par device
        
        
        /*else {
            devices[GPU_INDEX] = new cl_device_id[numDevices[0]];
            clGetDeviceIDs(platform, CL_DEVICE_TYPE_GPU, devices[GPU_INDEX].length, devices[GPU_INDEX], null);
        }*/

        

        //obtain the first gpu in the list of devices
        cl_device_id devices[] = new cl_device_id[numDevices[0]];
        clGetDeviceIDs(platform, CL_DEVICE_TYPE_GPU, devices.length, devices, null);
        device = devices[0];


        //int value[] = new int[2];
        //clGetDeviceInfo(device, CL_DEVICE_MAX_COMPUTE_UNITS, Sizeof.cl_int * 2, Pointer.to(value), null);
        //CL_DEVICE_GLOBAL_MEM_SIZE
        //CL_DEVICE_IMAGE_SUPPORT
        //CL_DEVICE_IMAGE2D_MAX_HEIGHT
        //CL_DEVICE_IMAGE2D_MAX_WIDTH
        //CL_DEVICE_MAX_COMPUTE_UNITS
        //CL_DEVICE_MAX_WORK_GROUP_SIZE
        //CL_DEVICE_NAME
       
        //create the context for this device
        context = clCreateContext(contextProperties, 1, new cl_device_id[] { device }, null, null, null);
         
        //set the queue properties
        cl_queue_properties properties = new cl_queue_properties();
        properties.addProperty(CL_QUEUE_PROFILING_ENABLE, 1);
        properties.addProperty(CL_QUEUE_OUT_OF_ORDER_EXEC_MODE_ENABLE, 1);

        //create the command queue
        commandQueue = clCreateCommandQueueWithProperties(context, device, properties, null);

        //create the program for this context
        program = clCreateProgramWithSource(context, 1, new String[] { read(source) }, null, null);
        clBuildProgram(program, 0, null, null, null, null);

        //create the kernel
        kernel = clCreateKernel(program, function, null);
    }

    private static String read(String filename) {
        String program = "";

        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNextLine())
                program += scanner.nextLine() + "\n";
            scanner.close();
        } catch (FileNotFoundException exception) {
            // TODO Auto-generated catch block
            exception.printStackTrace();
        }

        return program;
    }

    public static void release() {
        clRetainDevice(device);
        clUnloadPlatformCompiler(platform);
        clReleaseProgram(program);
        clReleaseKernel(kernel);
        clReleaseCommandQueue(commandQueue);
        clReleaseContext(context);
    }

    //TODO: mettre la texture dans la bounds
    //!sans doute pointer le canvas, charger le buffer dans la mémoire gpu
    public static void XXX(final BufferedImage output_image, final BufferedImage input_image, float x, float y) {
        cl_image_format format = new cl_image_format();
        format.image_channel_order = CL_RGBA;
        format.image_channel_data_type = CL_UNSIGNED_INT8;

        //allocate ouput pointer
        cl_image_desc output_description = new cl_image_desc();
        output_description.buffer = null; //must be null for 2D image
        output_description.image_depth = 0; //is only used if the image is a 3D image
        output_description.image_row_pitch = 0; //must be 0 if host_ptr is null
        output_description.image_slice_pitch = 0; //must be 0 if host_ptr is null
        output_description.num_mip_levels = 0; //must be 0
        output_description.num_samples = 0; //must be 0
        output_description.image_type = CL_MEM_OBJECT_IMAGE2D;
        output_description.image_width = output_image.getWidth();
        output_description.image_height = output_image.getHeight();
        output_description.image_array_size = output_description.image_width * output_description.image_height;

        cl_mem output_memory = clCreateImage(context, CL_MEM_WRITE_ONLY, format, output_description, null, null);
        
        //set up first kernel arg
        clSetKernelArg(kernel, 0, Sizeof.cl_mem, Pointer.to(output_memory));
        
        //allocates input pointer
        cl_image_desc input_description = new cl_image_desc();
        input_description.buffer = null; //must be null for 2D image
        input_description.image_depth = 0; //is only used if the image is a 3D image
        input_description.image_row_pitch = 0; //must be 0 if host_ptr is null
        input_description.image_slice_pitch = 0; //must be 0 if host_ptr is null
        input_description.num_mip_levels = 0; //must be 0
        input_description.num_samples = 0; //must be 0
        input_description.image_type = CL_MEM_OBJECT_IMAGE2D;
        input_description.image_width = input_image.getWidth();
        input_description.image_height = input_image.getHeight();
        input_description.image_array_size = input_description.image_width * input_description.image_height;

        DataBufferInt input_buffer = (DataBufferInt) input_image.getRaster().getDataBuffer();
        int input_data[] = input_buffer.getData();

        cl_mem input_memory = clCreateImage(context, CL_MEM_READ_ONLY | CL_MEM_USE_HOST_PTR, format, input_description, Pointer.to(input_data), null);

        //loads the input buffer to the gpu memory
        long[] input_origin = new long[] { 0, 0, 0 };
        long[] input_region = new long[] { input_image.getWidth(), input_image.getHeight(), 1 };
        int input_row_pitch = input_image.getWidth() * Sizeof.cl_uint; //the length of each row in bytes
        clEnqueueWriteImage(commandQueue, input_memory, CL_TRUE, input_origin, input_region, input_row_pitch, 0, Pointer.to(input_data), 0, null, null);
        
        //set up second kernel arg
        clSetKernelArg(kernel, 1, Sizeof.cl_mem, Pointer.to(input_memory));

        //set up third and fourth kernel args
        clSetKernelArg(kernel, 2, Sizeof.cl_float, Pointer.to(new float[] { x }));
        clSetKernelArg(kernel, 3, Sizeof.cl_float, Pointer.to(new float[] { y }));
        
        //blocks until all previously queued commands are issued
        clFinish(commandQueue);

        //enqueue the program execution
        long[] globalWorkSize = new long[] { input_description.image_width, input_description.image_height };

        // localWorkSize = 
        //workGroups = global / local
        clEnqueueNDRangeKernel(commandQueue, kernel, 2, null, globalWorkSize, null, 0, null, null);
        
        System.out.println("test");
        //transfer the output result back to host
        DataBufferInt output_buffer = (DataBufferInt) output_image.getRaster().getDataBuffer();
        int output_data[] = output_buffer.getData();
        long[] output_origin = new long[] { 0, 0, 0 };
        long[] output_region = new long[] { output_description.image_width, output_description.image_height, 1 };
        int output_row_pitch = output_image.getWidth() * Sizeof.cl_uint;
        clEnqueueReadImage(commandQueue, output_memory, CL_TRUE, output_origin, output_region, output_row_pitch, 0, Pointer.to(output_data), 0, null, null);

        System.exit(1);

        //free pointers
        clReleaseMemObject(input_memory);
        clReleaseMemObject(output_memory);
    }
}
