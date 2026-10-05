import os
import struct
import zlib


ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
OUT = os.path.join(ROOT, "src", "main", "resources", "assets", "pokebag", "textures", "items")


def rgba(hex_color):
    hex_color = hex_color.lstrip("#")
    return tuple(int(hex_color[index:index + 2], 16) for index in (0, 2, 4)) + (255,)


TRANSPARENT = (0, 0, 0, 0)
BLACK = rgba("#151515")
SHADOW = rgba("#2f2f35")
WHITE = rgba("#f2f2e8")
LIGHT = rgba("#ffffff")
STEEL = rgba("#8b8b94")


def write_png(path, pixels, width=16, height=16):
    raw = bytearray()
    for y in range(height):
        raw.append(0)
        for x in range(width):
            raw.extend(pixels[y][x])

    def chunk(kind, data):
        body = kind + data
        return struct.pack(">I", len(data)) + body + struct.pack(">I", zlib.crc32(body) & 0xFFFFFFFF)

    png = bytearray()
    png.extend(b"\x89PNG\r\n\x1a\n")
    png.extend(chunk(b"IHDR", struct.pack(">IIBBBBB", width, height, 8, 6, 0, 0, 0)))
    png.extend(chunk(b"IDAT", zlib.compress(bytes(raw), 9)))
    png.extend(chunk(b"IEND", b""))
    with open(path, "wb") as handle:
        handle.write(png)


def rect(pixels, x1, y1, x2, y2, color):
    for y in range(y1, y2 + 1):
        for x in range(x1, x2 + 1):
            if 0 <= x < 16 and 0 <= y < 16:
                pixels[y][x] = color


def pixel(pixels, x, y, color):
    if 0 <= x < 16 and 0 <= y < 16:
        pixels[y][x] = color


def make_sprite(primary, secondary, accent, button):
    pixels = [[TRANSPARENT for _ in range(16)] for _ in range(16)]
    primary = rgba(primary)
    secondary = rgba(secondary)
    accent = rgba(accent)
    button = rgba(button)

    rect(pixels, 5, 1, 10, 1, BLACK)
    rect(pixels, 4, 2, 5, 3, BLACK)
    rect(pixels, 10, 2, 11, 3, BLACK)
    rect(pixels, 6, 2, 9, 2, STEEL)

    rect(pixels, 3, 4, 12, 4, BLACK)
    rect(pixels, 2, 5, 13, 12, BLACK)
    rect(pixels, 3, 13, 12, 14, BLACK)

    rect(pixels, 3, 5, 12, 7, primary)
    rect(pixels, 3, 9, 12, 12, secondary)
    rect(pixels, 4, 13, 11, 13, secondary)
    rect(pixels, 3, 8, 12, 8, BLACK)
    rect(pixels, 4, 5, 5, 6, LIGHT)
    pixel(pixels, 6, 5, LIGHT)

    rect(pixels, 6, 7, 9, 10, BLACK)
    rect(pixels, 7, 8, 8, 9, button)

    rect(pixels, 2, 6, 2, 11, SHADOW)
    rect(pixels, 13, 6, 13, 11, SHADOW)
    rect(pixels, 3, 14, 12, 14, SHADOW)

    rect(pixels, 4, 10, 5, 13, accent)
    rect(pixels, 10, 10, 11, 13, accent)
    pixel(pixels, 4, 9, BLACK)
    pixel(pixels, 11, 9, BLACK)

    return pixels


def main():
    os.makedirs(OUT, exist_ok=True)
    sprites = {
        "pokebag_normal.png": make_sprite("#d73535", "#f2f2e8", "#8a4c25", "#dcdcdc"),
        "pokebag_great.png": make_sprite("#2f64c8", "#f2f2e8", "#d63333", "#dcdcdc"),
        "pokebag_ultra.png": make_sprite("#242424", "#f2f2e8", "#f3d142", "#dcdcdc"),
        "pokebag_master.png": make_sprite("#7b42bb", "#f2f2e8", "#ec7eb4", "#dcdcdc"),
    }
    for name, pixels in sprites.items():
        write_png(os.path.join(OUT, name), pixels)


if __name__ == "__main__":
    main()
