package ml.stargirls.maia.paper.serialize;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public interface ItemSerialization {
	static @Nullable String serializeItems(@Nullable ItemStack[] items) throws IOException {
		if (items == null) {
			return null;
		}

		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
		try (BukkitObjectOutputStream output = new BukkitObjectOutputStream(byteOutputStream)) {
			output.writeInt(items.length);

			for (ItemStack item : items) {
				output.writeObject(item);
			}

			return Base64Coder.encodeLines(byteOutputStream.toByteArray());
		}
	}

	static @Nullable ItemStack[] deserializeItems(@Nullable String serializedItems)
		throws IOException, ClassNotFoundException {
		if (serializedItems == null) {
			return null;
		}

		try (
			BukkitObjectInputStream dataInput = new BukkitObjectInputStream(new ByteArrayInputStream(
				Base64Coder.decodeLines(serializedItems)))
		) {
			ItemStack[] items = new ItemStack[dataInput.readInt()];

			for (int i = 0; i < items.length; i++) {
				Object object = dataInput.readObject();

				if (object == null) {
					items[i] = null;
					continue;
				}

				items[i] = (ItemStack) object;
			}

			return items;
		}
	}
}
