package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.I2cController;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceReader;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import java.util.concurrent.locks.Lock;

/**
 * Created by Charles on 9/17/2015.
 */
public class GyroTest  extends OpMode implements I2cController.I2cPortReadyCallback{

    I2cDevice gyro;
    int GyroAdress = 32;
    int Z_Adress = 12;
    int ReadLength = 15;
    byte[] readCache;
    byte[] writeCache;
    Lock readLock;
    Lock writeLock;
    int final_value = 0;


    public static final short ByteToShort(byte[] b)
    {
        short s = 0;
        s |= b[0] & 0xFF;
        s <<= 8;
        s |= b[1] & 0xFF;
        return s;
    }


    public GyroTest(I2cDevice gyroIn) {

        gyro = gyroIn;

        this.readCache = gyro.getI2cReadCache();
        this.readLock = gyro.getI2cReadCacheLock();
        this.writeCache = gyro.getI2cWriteCache();
        this.writeLock = gyro.getI2cWriteCacheLock();
        gyro.enableI2cReadMode(GyroAdress, Z_Adress, ReadLength);
        gyro.setI2cPortActionFlag();
        gyro.writeI2cCacheToController();
        gyro.registerForI2cPortReadyCallback(this);

    }

    @Override
    public void init() {

        gyro = hardwareMap.i2cDevice.get("Gyro");





    }

    double power = 0;
    @Override
    public void portIsReady( int i)
    {

    }

    public int valueOf(int inputLSB, int inputMSB)  {
        byte lsb;
        byte msb;

        try{
            readLock.lock();

            lsb = readCache[inputLSB];
            msb = readCache[inputMSB];
        }
        finally{
            readLock.unlock();
        }
        return ((msb << 8) & 0xff00 | (lsb & 0x00ff));
    }

    @Override
    public void loop() {





        final_value = valueOf( 5,6);





        // Feed me back the info to tell me if its encoder running or by power
        telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData("Gyro","" + final_value);
        telemetry.addData("Read Mode?","" + gyro.isI2cPortInReadMode());
        telemetry.addData("Ready?","" + gyro.isI2cPortReady());
    }


}
