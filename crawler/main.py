from get_safe_restaurant import register_restaurant
import time


if __name__ == '__main__':
    start = time.time()
    while True:
        if (time.time()-start) % 10 == 0:
            register_restaurant()
